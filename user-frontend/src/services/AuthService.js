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
}

export default new AuthService();
