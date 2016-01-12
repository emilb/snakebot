import React from 'react';
import Repos from './GitHub/Repos';
import UserProfile from './GitHub/UserProfile';
import Notes from'./Notes/Notes';
import getGitHubInfo from '../utils/helpers';
//import Rebase from 're-base';


class Profile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            notes: [],
            bio: {},
            repos: []
        }
    }

    handleAddNote(newNote) {
        //base.post(this.props.params.username, {
        //    data: this.state.notes.concat([newNote])
    //})
    };

    componentDidMount() {
        this.init(this.props.params.username);
    };

    componentWillUnmount() {

    };

    init(username) {
        getGitHubInfo(username)
            .then(function (data) {
                this.setState({
                    bio: data.bio,
                    repos: data.repos
                })
            }.bind(this))
    };

    componentWillReceiveProps(nextProps) {
        this.init(nextProps.params.username);
    };

    render() {
        return (
            <div className="row">
                <div className="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                    <UserProfile username={this.props.params.username} bio={this.state.bio}/>
                </div>
                <div className="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                    <Repos username={this.props.params.username} repos={this.state.repos}/>
                </div>
                <div className="col-xs-4 col-sm-4 col-md-4 col-lg-4">
                    <Notes
                        username={this.props.params.username}
                        notes={this.state.notes}
                        addNote={(newNote) => this.handleAddNote(newNote)}/>
                </div>
            </div>
        )
    }
}

export default Profile;