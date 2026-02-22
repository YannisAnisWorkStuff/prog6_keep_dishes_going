import {type PropsWithChildren, useEffect, useState} from 'react';
import SecurityContext from './SecurityContext';
import {addAccessTokenToAuthHeader, removeAccessTokenFromAuthHeader} from '../services/Auth';
import {isExpired} from 'react-jwt';
import Keycloak, {type KeycloakConfig} from 'keycloak-js';
import type {User} from '../model/User';

const keycloakConfig: KeycloakConfig = {
    url: import.meta.env.VITE_KC_URL,
    realm: import.meta.env.VITE_KC_REALM,
    clientId: import.meta.env.VITE_KC_CLIENT_ID,
};

const keycloak = new Keycloak(keycloakConfig);

export default function SecurityContextProvider({children}: PropsWithChildren) {
    const [loggedInUser, setLoggedInUser] = useState<User | undefined>(undefined);
    const [isInitialised, setIsInitialised] = useState(false);

    useEffect(() => {
        keycloak
            .init({
                onLoad: 'check-sso',
                checkLoginIframe: false
            })
            .then((/* authenticated */) => {
                setIsInitialised(true);
                if (keycloak.token) {
                    addAccessTokenToAuthHeader(keycloak.token);
                    updateUserFromToken();
                }
            })
            .catch(() => setIsInitialised(true));
    }, []);

    keycloak.onAuthSuccess = () => {
        addAccessTokenToAuthHeader(keycloak.token);
        updateUserFromToken();
    };

    keycloak.onAuthLogout = () => {
        removeAccessTokenFromAuthHeader();
        setLoggedInUser(undefined);
    };

    keycloak.onAuthError = () => {
        removeAccessTokenFromAuthHeader();
        setLoggedInUser(undefined);
    };

    keycloak.onTokenExpired = () => {
        keycloak.updateToken(-1).then(() => {
            addAccessTokenToAuthHeader(keycloak.token);
            updateUserFromToken();
        });
    };

    function login() {
        keycloak.login({
            redirectUri: `${window.location.origin}/restaurants`,
        });
    }

    function logout() {
        keycloak.logout({
            redirectUri: `${window.location.origin}/`,
        });
    }

    function isAuthenticated() {
        return keycloak.token ? !isExpired(keycloak.token) : false;
    }

    function updateUserFromToken() {
        if (!keycloak.idTokenParsed || !keycloak.tokenParsed) return;

        const name =
            (keycloak.idTokenParsed.given_name as string) ??
            (keycloak.idTokenParsed.name as string) ??
            (keycloak.tokenParsed.preferred_username as string) ??
            'User';

        const email =
            (keycloak.idTokenParsed.email as string) ??
            (keycloak.tokenParsed.email as string) ??
            (keycloak.tokenParsed.preferred_username as string) ??
            '';

        const realmRolesRaw = (keycloak.tokenParsed.realm_access?.roles as string[]) ?? [];
        const realmRoles = realmRolesRaw.filter(
            (r) => !r.startsWith('default-') && r !== 'offline_access' && r !== 'uma_authorization'
        );

        setLoggedInUser({
            email,
            name,
            roles: realmRoles,
        });
    }

    return (
        <SecurityContext.Provider
            value={{
                isInitialised,
                isAuthenticated,
                loggedInUser,
                login,
                logout,
            }}
        >
            {children}
        </SecurityContext.Provider>
    );
}