package pr3.task3;

import io.reactivex.Observable;

import java.util.List;

import static io.reactivex.Flowable.fromArray;
import static java.util.stream.Collectors.toList;

public class Task3 {
    public static class UserFriend {
        private final int userId;
        private final int friendId;

        public UserFriend(int userId, int friendId) {
            this.userId = userId;
            this.friendId = friendId;
        }
        public int getUserId() {
            return userId;
        }
        public int getFriendId() {
            return friendId;
        }


        static Observable<UserFriend> getFriends(int userId) {
            return Observable.fromArray(
                    new UserFriend(userId,  2),
                    new UserFriend(userId, 3),
                    new UserFriend(userId, 4),
                    new UserFriend(userId, 5),
                    new UserFriend(userId, 6)
            );
        }
    }

    public static void main(String[] args) {
        Integer[] ids = {1, 2, 3, 4, 5, 6};

        Observable<Integer> idStream = Observable.fromArray(ids);

        Observable<UserFriend> friends = idStream.flatMap(userId -> UserFriend.getFriends(userId));
        friends.subscribe(user -> { System.out.println("User " + user.getUserId() + " friends: " + user.getFriendId());}
        );
    }
}
