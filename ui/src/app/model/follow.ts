import {Deserializable} from './deserializable';

export class Follow implements Deserializable {
    followid: number;
    followerUserid: string;
    followingUserid: string;

    deserialize(input: any): this {
        return Object.assign(this, input);
    }
}
