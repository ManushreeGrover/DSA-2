#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
#include <queue>
using namespace std;

class SocialNetwork {
    unordered_map<int, unordered_set<int>> friends;

public:
    void addUser(int user_id) {
        friends[user_id];  // Initializes an empty set for the user
        cout << "User " << user_id << " added.\n";
    }

    void addFriendship(int user1, int user2) {
        friends[user1].insert(user2);
        friends[user2].insert(user1);
        cout << "Friendship established between " << user1 << " and " << user2 << ".\n";
    }

    vector<int> getFriendRecommendations(int user_id) {
        unordered_set<int> visited;
        vector<int> recommendations;
        for (int friend_id : friends[user_id]) {
            for (int potential : friends[friend_id]) {
                if (potential != user_id && friends[user_id].find(potential) == friends[user_id].end()) {
                    visited.insert(potential);
                }
            }
        }
        recommendations.assign(visited.begin(), visited.end());
        return recommendations;
    }

    void viewFriends(int user_id) {
        cout << "Friends of " << user_id << ": ";
        for (int friend_id : friends[user_id]) {
            cout << friend_id << " ";
        }
        cout << "\n";
    }
};

int main() {
    SocialNetwork sn;
    sn.addUser(1);
    sn.addUser(2);
    sn.addUser(3);
    sn.addFriendship(1, 2);
    sn.addFriendship(2, 3);
    vector<int> recommendations = sn.getFriendRecommendations(1);
    cout << "Friend Recommendations for 1: ";
    for (int user : recommendations) {
        cout << user << " ";
    }
    cout << "\n";
    sn.viewFriends(1);
    return 0;
}