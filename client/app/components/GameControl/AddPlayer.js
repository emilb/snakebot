import React from 'react';
import addPlayer from '../../utils/helpers';

class AddPlayer extends React.Component {
    getRef(name) {
        this.playerName = name;
    };

    handleAddPlayer() {
        const playerName = this.playerName.value;
        this.playerName.value = '';
        this.props.player1.id = addPlayer(playerName);
    };

    render() {
        return (
                <form onSubmit={() => this.handleAddPlayer()}>
                    <div className="form-group col-sm-2">
                        <input type="text" className="form-control" ref={(ref) => this.getRef(ref)}/>
                    </div>
                    <div className="form-group col-sm-1">
                        <button type="submit" className="btn btn-block btn-primary"> Add Player</button>
                    </div>
                </form>
        )
    }
}

export default AddPlayer;
