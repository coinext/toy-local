import React, { Component } from 'react'
import Router from 'next/router'
import ClientOAuth2 from 'client-oauth2';
import 'semantic-ui-css/semantic.min.css'
import '../css/form.css'
import { Button, Checkbox, Form } from 'semantic-ui-react'
import getConfig from 'next/config'

export default class Login extends Component {
    constructor(props) {
        super(props)
        this.handleSubmit = this.handleSubmit.bind(this)
    }
    
    handleSubmit (e) {
        e.preventDefault()

        const { publicRuntimeConfig } = getConfig()

        const auth = new ClientOAuth2({
            clientId: publicRuntimeConfig.clientId,
            clientSecret: publicRuntimeConfig.clientSecret,
            accessTokenUri: `${publicRuntimeConfig.oauthEndpoint}/oauth/token`,
            scopes: ['read']
        })

        auth.owner.getToken(this.refs.email.value, this.refs.password.value)
            .then(user =>  {
                localStorage.setItem('token', user.tokenType + ' ' + user.accessToken)
                Router.push('/')
            })
            .catch(err => {
            })
    }
    
    render() {
        return (
            <Form onSubmit={this.handleSubmit}>
                <Form.Field>
                    <label>email</label>
                    <input type="text" ref="email" />
                </Form.Field>
                <Form.Field>
                    <label>password</label>
                    <input type="password" ref="password" />
                </Form.Field>
                <Button type='submit'>Submit</Button>
            </Form>
        )
    }
}
