import {Deserializable} from './deserializable';

export class Tweet implements Deserializable {
    tweetid: number;
    tweet: string;
    userid: string;

    deserialize(input: any): this {
        return Object.assign(this, input);
    }
}
