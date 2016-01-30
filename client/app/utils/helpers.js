import axios from 'axios'

export default function startNewGame() {
    return axios.get(`http://localhost:8080/newGame`)
        .then((world) => ({world: world.data}));
}

//export function addPlayer(username) {
//    return axios.get(`https://localhost:8080/addPlayer?name=${username}`)
//        .then((id) => ({id: id}))
//}
//
//export function startGame() {
//    return axios.post(`https://localhost:8080/startGame`);
//}
//
//export function makeMove(id, direction) {
//    return axios.get(`https://localhost:8080/makeMove?id=${id};direction=${direction}`)
//        .then((world) => ({world: world}));
//}

//export default function getGitHubInfo(username) {
//    return axios.all([getRepos(username), getUserInfo(username)])
//        .then((arr) => ({repos: arr[0].data, bio: arr[1].data}))
//}


