package net.davepkennedy

import java.net.{URI, URL}
import java.io.{InputStreamReader, BufferedReader, BufferedInputStream}
import javax.ws.rs.core.MediaType
import javax.ws.rs.{QueryParam, GET, Produces, Path}


/**
 * Created with IntelliJ IDEA.
 * User: dave
 * Date: 18/05/2012
 * Time: 21:09
 * To change this template use File | Settings | File Templates.
 */

@Path("/count")
class CharCountResource {
  def fetch(url: String) = {
    new BufferedReader (
      new InputStreamReader (
        new URL(url).openConnection().getInputStream
      )
    )
  }

  def count_chars(reader: BufferedReader, count: Int = 0): Int = reader.readLine match {
    case null => count
    case s:String => count_chars(reader, count+s.length)
  }

  @GET
  @Produces (Array(MediaType.TEXT_PLAIN))
  def process_request (@QueryParam("address") address: String): String = count_chars(fetch(address)).toString

}
