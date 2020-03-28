import {Deserializable} from './deserializable';

export class Reply implements Deserializable {
    replyid: number;
    reply: string;
    tweetid: number;
    userid: string;

    deserialize(input: any): this {
        return Object.assign(this, input);
    }
}
