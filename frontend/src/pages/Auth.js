import React, { Component } from 'react';
import "./Auth.css";


class AuthPage extends Component {
    


    state = {
        isLogin: true,
    }




    constructor(props) {
        super(props);
        this.emailElement = React.createRef();
        this.passwordElement = React.createRef();
    }
    switchModeHandler = () => {
        this.setState((prevState) => {
            return { isLogin: !prevState.isLogin }
        })
        
    }
    submitHandler = (event) => {

        event.preventDefault();
        const email = this.emailElement.current.value;
        const password = this.passwordElement.current.value;

        if (email.trim().length === 0 || password.trim().length === 0) {
            return () => {
                alert("error")
            };
        };

        let requestBody = {
            query: `
                query{
                    login(loginInput:{
                        email:"${email}",
                        password:"${password}"
                    }){
                        userId,
                        token,
                        tokenExpiration
                    }
                }
            `
        }


        if (!this.state.isLogin) {
            requestBody = {
                query: `
                    mutation{
                        createUser(userInput:{
                            email:"${email}"
                            password:"${password}"
                        }){
                            id
                            email
                        }
                    }
                    `
            };
        }
        fetch("http://localhost:8080/graphql", {
            method: "POST",
            body: JSON.stringify(requestBody),
            header: {
                "Content-Type": "application/json"
            },
        }).then((response) => {
            if (response.status !== 200 && response.status !== 201) {
                throw new Error("Something Failed!")
            }
            return response.json()
        }).then((responseData) => {
            console.log(responseData)
        }).catch((error => {
            console.log(error)
        }))
    };



    render() {
        return (
            <form className="auth-form" onSubmit={this.submitHandler}>
                <div className="popMessage"> {this.state.isLogin ? "登録" : "ログイン"}</div>

                <div className="form-control">
                    <label htmlFor="email">E-mail</label>
                    <input type='email' id="email" ref={this.emailElement}></input>
                </div>
                <div className="form-control">
                    <label htmlFor="password" >パスワード</label>
                    <input type="password" id="password" ref={this.passwordElement}></input>
                </div>
                <div className="form-actions">
                    <button type="submit">{this.state.isLogin ? "ログイン" : "登録"}</button>
                    <button type="button" onClick={this.switchModeHandler} >
                        {this.state.isLogin ? "登録" : "ログイン"}に切り替える
                    </button>
                </div>
            </form>
        );
    }
}

export default AuthPage;