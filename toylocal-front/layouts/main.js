import 'semantic-ui-css/semantic.min.css'
import Header from '../components/header'
import Footer from '../components/footer'

const Main = ({children}) => (
    <div>
        <Header/>
            <div style={{padding: '2em'}}>
                { children }
            </div>
        <Footer />
  </div>
);

export default Main;