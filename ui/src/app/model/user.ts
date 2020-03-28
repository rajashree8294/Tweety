import {Tweet} from './tweet';
import {Reply} from './reply';
import {Connection} from './connection';
import {Follow} from './follow';
import {Deserializable} from './deserializable';

export class User implements Deserializable {
    public userid: string;
    public emailid: string;
    public password: string;
    public fname: string;
    public lname: string;
    public role: string;
    public tweets: Tweet[];
    public replies: Reply[];
    public connections: Connection[];
    public follows: Follow[];

    deserialize(input: any): this {
         // Assign input to our object BEFORE deserialize our custom objects to prevent already deserialized objects from being overwritten.
        Object.assign(this, input);

        // Iterate over all custon objects for our user and map them to a proper model
        this.tweets = input.tweets.map((t: any) => new Tweet().deserialize(t));
        this.replies = input.replies.map((r: any) => new Reply().deserialize(r));
        this.connections = input.connections.map((c: any) => new Connection().deserialize(c));
        this.follows = input.followings.map((f: any) => new Follow().deserialize(f));

        return this;
    }

}
