# Stream Screen From OBS to Minecraft Map
## Description
This is a simple project that allows you to stream your OBS screen or webcam to a Minecraft map.

## Requirements
- [OBS Studio](https://obsproject.com/)
- [FFMPEG](https://ffmpeg.org/)

## Installation
1. Put the plugin in the plugins folder of your Spigot/paper server.
2. Start the server or reload the server.
3. Run the command `/generateScreen <width> <height>` in the server console or in-game.
4. In a terminal after launching the obs virtual camera run 
```bash
ffmpeg -y -i /dev/<your webcam number like video*> -vf scale=768:512 -f rawvideo -c:v mjpeg -qscale:v 1 -r 60 udp://127.0.0.1:1337
```
5. On windows you can use the following command
```bash
ffmpeg -y -f dshow -i video="<your webcam name>" -vf scale=768:512 -f rawvideo -c:v mjpeg -qscale:v 1 -r 60 udp://127.0.0.1:1337
```

NOTE: You can change the resolution to whatever you want, generateScreen 1 1 will generate a 1x1 map and you will need to change scale to 128:128.

### Scale to width and height

|ffmpeg scale|width|height|
|------------|-----|------|
| 128:128    | 1   | 1    |
| 256:256    | 2   | 2    |
| 512:512    | 4   | 4    |
| 768:512    | 6   | 4    |
| 1024:1024  | 8   | 8    |
| 1536:1024  | 12  | 8    |
| ect...     |     |      |

## Images 

![screen 1](https://github.com/EthannSchneider/StreamScreen/wiki/img/huge_2024-08-12_12.54.26.png)
![screen 2](https://github.com/EthannSchneider/StreamScreen/wiki/img/huge_2024-08-12_12.54.55.png)

## Sources 

- https://github.com/makidoll/MakiScreen
- https://stackoverflow.com/questions/21420252/how-to-receive-mpeg-ts-stream-over-udp-from-ffmpeg-in-java

## License

This project is licensed under the terms of the MIT license.

- [MIT](LICENSE)