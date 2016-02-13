import React from 'react';
import getGame from '../../utils/helpers';

class GetGame extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
           hans: {}
        }
    }

    handleSubmit() {
        this.setState({hans: "assasa"});
        this.props.addGame()
    };

    render() {
        return (
            <div className="input-group">
                <span className="input-btn-group">
                    <button className="btn btn-default" type="button" onclick={() => this.handleSubmit()}> Submit </button>
                </span>

                <div>
                   HAns is: {hans}
                </div>
            </div>
        )
    }
}


GetGame.propTypes = {
    addGame: React.PropTypes.func.isRequired
};

export default GetGame;
