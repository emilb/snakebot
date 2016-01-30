import React from 'react';
import startGame from '../../utils/helpers';

class StartGame extends React.Component {
    handleStartGame() {
        startGame();
    };

    render() {
        return (
            <div className="form-group col-sm-3">
                <button onclick={() => this.handleStartGame()} type="submit" className="btn btn-block btn-primary">
                    Start Game
                </button>
            </div>
        )
    }
}

export default StartGame;
