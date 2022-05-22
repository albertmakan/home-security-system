import httpClient from "../config/httpClient";


class AuthService {

    login(values) {
        var { username, password } = values;
        return httpClient({
            url: "auth/login",
            method: "POST",
            data: { username: username, password: password },
        })
    }

    whoAmI() {
        return httpClient({
            url: "auth/whoami",
            method: "GET",
        })
    }

    revokeToken() {
        return httpClient({
            url: "auth/revokeJWT",
            method: "POST",
        })
    }
}

export default new AuthService();
