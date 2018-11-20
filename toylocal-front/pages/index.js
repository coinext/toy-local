import React, { Component } from 'react'
import Router from 'next/router'
import Page from '../layouts/main'

export default class Index extends Component {
    componentDidMount() {
        if (!localStorage.getItem('token')) {
            Router.push('/login')
        }
    }

    render() {
        return (
            <Page>
                <h1>
                    HOME
                </h1>
            </Page>
        )
    }
}