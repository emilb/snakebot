import React from 'react';

class NewGame extends React.Component {
    handleNewGame() {
        this.props.newGame();
    };

    render() {
        return (
                <div className="form-group col-sm-3 col-md-3">
                    <button onClick={() => this.handleNewGame()} type="submit" className="btn btn-block btn-primary">
                        New Game
                    </button>
                </div>
        )
    }
}

NewGame.propTypes = {
    newGame: React.PropTypes.func.isRequired
};

export default NewGame;
