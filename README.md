#Weather Radar TEST
![Radar Base Image](https://raw.githubusercontent.com/Bingram/WUnderGround_TEST/master/CurrentBounds-PNW.png)
*This is the base image of the current area Weather Radar watches*

##A backend server for retrieving weather information and determining whether weather is incoming

The weather radar started as a simple idea of being able to have a preemptive warning system for incoming rain 
when I was outside for the afternoon, either working in my yard, at a nearby park or on any outdoor event.

The larger idea is to have a system that retrieves the weather information and distributes out
to all registered users, and their device can determine where the weather is relative to
their set boundary marker. 

This eliminates the need for a backend server to calculate the likelihood of inclement weather
for each user. Also reducing the number of calls to the WunderGround API, but transferring the load
to the Radar server to distribute the weather information to all users.


##Functionality

###Weather Array
The server creates a 2D array of pixel values taken from a PNG image file of
current weather given set bounds and resolution. The image file is converted into
a 2D array for reference against a Boundary as set by a user.

#_CURRENTLY ONLY PNW_

###Boundary System 
The main function of the Boundary system is based on the MidPoint Circle algorithm

```java
public static void DrawCircle(int x0, int y0, int radius)
{
  int x = radius;
  int y = 0;
  int radiusError = 1-x;
 
  while(x >= y)
  {
    DrawPixel(x + x0, y + y0);
    DrawPixel(y + x0, x + y0);
    DrawPixel(-x + x0, y + y0);
    DrawPixel(-y + x0, x + y0);
    DrawPixel(-x + x0, -y + y0);
    DrawPixel(-y + x0, -x + y0);
    DrawPixel(x + x0, -y + y0);
    DrawPixel(y + x0, -x + y0);
    y++;
    if (radiusError<0)
    {
      radiusError += 2 * y + 1;
    }
    else
    {
      x--;
      radiusError += 2 * (y - x) + 1;
    }
  }
}
```

This calculates a boundary circle that can be referenced against a 2D array of the current 
weather conditions. This allows for checking each point on a Boundary against the matching 
point on the weather array, instead of checking every point on the array.

##Quadrants

Each Boundary is composed of quadrants which correspond to the 8 cardinal directions, this 
gives a better granularity for smarter checking. In theory a user could set the direction to
start checking on each update, or for the current wind direction to dictate where the check
starts.

Each Boundary currently only supports a single level of checking at the outermost Boundary. 
Future implementations will have multiple levels for checking in order to gauge a time frame
for when weather is expected to arrive at the center of the designated Boundary.



#TODO

- [X] Make weatherMap more modular for server/client
- [X] Implement basic bounds checking
- [] Implement server iteration (RESTful?)
- [] Fork for client
    - [] Web client
    - [] Android client
    - []

