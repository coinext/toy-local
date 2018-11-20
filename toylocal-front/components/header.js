import React, { Component } from 'react'
import { Menu } from 'semantic-ui-react'

export default class Header extends Component {
    render() {
      return (
        <div>
          <Menu pointing secondary>
            <Menu.Item as="a" href="/"
                name='home'
            />
            <Menu.Item as="a" href="/search"
              name='search'
            />
            <Menu.Item as="a" href="/history"
              name='history'
            />
            <Menu.Item as="a" href="/bookmark"
              name='bookmark'
            />
            <Menu.Menu position='right'>
              <Menu.Item as="a" href="/logout"
                name='logout'
              />
            </Menu.Menu>
          </Menu>
        </div>
      )
    }
  }