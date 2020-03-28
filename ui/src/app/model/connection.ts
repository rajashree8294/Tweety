import {Deserializable} from './deserializable';

export class Connection implements Deserializable {
    connectionid: number;
    fromUserid: string;
    status: number;
    date: Date;

    deserialize(input: any): this {
        return Object.assign(this, input);
    }
}
