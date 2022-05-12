import  React,{ Component } from 'react';
import "./Auth.css"


class AuthPage extends Component {

    constructor(props) {
        super(props);
        this.emailElement = React.createRef();
        this.passwordElement = React.createRef();


    }

    submitHandler = (event) => {
        event.preventDefault();
        const email =this.emailElement.current.Value;
        const password =this.passwordElement.current.Value;

        if(email.trim().length===0 || password.trim().length===0){
            return;
        }
        console.log(email,password);
    }


    render() {
        return (
            <form className="auth-form" onSubmit={this.submitHandler}>
                <div className="form-control">
                    <label htmlFor="email">E-mail</label>
                    <input type='email' id="email" ref={this.emailElement}></input>
                </div>
                <div className="form-control">
                    <label htmlFor="password" ref={this.passwordElement}>パスワード</label>
                    <input type="password" id="password"></input>
                </div>
                <div className="form-actions">
                    <button type="submit">ログイン</button>
                </div>
            </form>
        );
    }
}

export default AuthPage;