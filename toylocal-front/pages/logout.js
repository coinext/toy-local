import React, { Component } from 'react'
import Router from 'next/router'
import Page from '../layouts/main'

export default class extends Component {
    componentDidMount() {
        localStorage.removeItem('token')
        Router.push('/login')
    }

    render() {
        return (
            <Page />
        )
    }
}