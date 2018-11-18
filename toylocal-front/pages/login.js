import Router from 'next/router'
import Axios from 'axios';
import ClientOAuth2 from 'client-oauth2';

class Login extends React.Component {
    constructor(props) {
        super(props)
        this.handleSubmit = this.handleSubmit.bind(this)
    }
    
    handleSubmit (e) {
        e.preventDefault();

        const auth = new ClientOAuth2({
            clientId: 'toylocal-id',
            clientSecret: 'toylocal-secret',
            accessTokenUri: 'http://52.79.128.170:8081/oauth/token',
            scopes: ['read']
        })

        auth.owner.getToken(this.refs.email.value, this.refs.password.value)
            .then(user =>  {
                Axios.defaults.headers.common['Authorization'] = user.tokenType + ' ' + user.accessToken;
                Router.push('/');
            })
            .catch(err => {
            })
    }
    
    render() {
        return (
            <div>
                Login
                <form onSubmit={this.handleSubmit} >
                    <input type="text" ref="email" value="user_a@mail.com"/>
                    <input type="password" ref="password" value="abc!@#"/>
                    <input type="submit" value="Submit"/>
                </form>
            </div>
        );
    }
}

export default Login;