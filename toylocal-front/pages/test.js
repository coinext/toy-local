import axios from 'axios';
import Page from '../layouts/main'
import ClientOAuth2 from 'client-oauth2';

class Test extends React.Component {
    static async getInitialProps ({req, res}) {
        const auth = new ClientOAuth2({
        clientId: 'toylocal-id',
        clientSecret: 'toylocal-secret',
        accessTokenUri: 'http://localhost:8081/oauth/token',
        scopes: ['read']
        })
        // console.log(githubAuth);
        const a = auth.owner.getToken('user_a@mail.com', 'abc!@#').then(function (user) {
            // console.log(user) //=> { accessToken: '...', tokenType: 'bearer', ... }
            axios.defaults.headers.common['Authorization'] = 'bearer ' + user.accessToken;
        })
        return {
            books: 'a'
        }
    }

    render() {
        return (
            <Page>
                <ul>
                    a
                </ul>
            </Page>
        );
    }
}

export default Test;