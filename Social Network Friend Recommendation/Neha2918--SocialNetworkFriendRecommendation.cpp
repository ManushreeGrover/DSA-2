#include <iostream>
#include <unordered_map>
#include <unordered_set>
#include <vector>
#include <queue>
#include <algorithm> // for sorting

class SocialNetwork {
    // Adjacency list representation for the graph
    std::unordered_map<int, std::unordered_set<int>> friends;

public:
    // Add a new user
    void addUser(int user_id) {
        if (friends.find(user_id) == friends.end()) {
            friends[user_id];  // Initialize an empty set for the user
            std::cout << "User " << user_id << " added.\n";
        } else {
            std::cout << "User " << user_id << " already exists.\n";
        }
    }

    // Add a friendship between two users
    void addFriendship(int user1, int user2) {
        if (friends.find(user1) == friends.end() || friends.find(user2) == friends.end()) {
            std::cout << "One or both users do not exist.\n";
            return;
        }
        if (friends[user1].count(user2)) {
            std::cout << "Friendship already exists between " << user1 << " and " << user2 << ".\n";
            return;
        }
        friends[user1].insert(user2);
        friends[user2].insert(user1);
        std::cout << "Friendship established between " << user1 << " and " << user2 << ".\n";
    }

    // View all friends of a user
    void viewFriends(int user_id) {
        if (friends.find(user_id) == friends.end()) {
            std::cout << "User " << user_id << " does not exist.\n";
            return;
        }
        std::cout << "Friends of " << user_id << ": ";
        for (int friend_id : friends[user_id]) {
            std::cout << friend_id << " ";
        }
        std::cout << "\n";
    }

    // Recommend friends for a given user
    std::vector<int> getFriendRecommendations(int user_id) {
        if (friends.find(user_id) == friends.end()) {
            std::cout << "User " << user_id << " does not exist.\n";
            return {};
        }

        std::unordered_map<int, int> mutualCount; // To count mutual friends
        std::unordered_set<int> visited;

        // Loop through direct friends
        for (int friend_id : friends[user_id]) {
            // Loop through friends of friends
            for (int potential_friend : friends[friend_id]) {
                if (potential_friend != user_id && friends[user_id].find(potential_friend) == friends[user_id].end()) {
                    mutualCount[potential_friend]++;
                }
            }
        }

        // Create a vector of recommendations sorted by the number of mutual friends
        std::vector<std::pair<int, int>> recommendations;
        for (auto it = mutualCount.begin(); it != mutualCount.end(); ++it) {
            recommendations.push_back({it->first, it->second});
        }

        // Sort by mutual friends (higher count first), break ties by user ID (lower ID first)
        std::sort(recommendations.begin(), recommendations.end(), [](const std::pair<int, int>& a, const std::pair<int, int>& b) {
            if (a.second == b.second) return a.first < b.first;
            return a.second > b.second;
        });

        // Extract only the user IDs
        std::vector<int> result;
        for (auto it = recommendations.begin(); it != recommendations.end(); ++it) {
            result.push_back(it->first);
        }

        return result;
    }
};

int main() {
    SocialNetwork sn;

    sn.addUser(1);
    sn.addUser(2);
    sn.addUser(3);
    sn.addUser(4);
    sn.addUser(5);

    sn.addFriendship(1, 2);
    sn.addFriendship(2, 3);
    sn.addFriendship(3, 4);
    sn.addFriendship(4, 5);

    std::cout << "\nFriend Recommendations for 1: ";
    std::vector<int> recommendations = sn.getFriendRecommendations(1);
    for (int user : recommendations) {
        std::cout << user << " ";
    }
    std::cout << "\n";

    sn.viewFriends(1);
    sn.viewFriends(3);

    return 0;
}
