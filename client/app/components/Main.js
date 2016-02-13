import React from 'react';
import GameBoard from './GameBoard';

class Main extends React.Component {
    render () {
        return (
            <div className="main-container">
                       <GameBoard />

                <div className="container">
                    {this.props.children}
                </div>
            </div>
        )
    }
}

export default Main;