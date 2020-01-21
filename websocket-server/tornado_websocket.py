import os.path

import tornado.httpserver
import tornado.web
import tornado.ioloop
import tornado.options
import tornado.httpclient
import tornado.websocket

import json


class IndexHandler(tornado.web.RequestHandler):
    def get(self):
        # self.render("index.html")
        print("got client myself")


class SocketHandler(tornado.websocket.WebSocketHandler):
    """docstring for SocketHandler"""
    clients = set()

    @staticmethod
    def send_to_all(message):
        for c in SocketHandler.clients:
            c.write_message(json.dumps(message))

    def open(self):
        self.write_message(json.dumps({
            'type': 'sys',
            'message': 'Welcome to WebSocket',
        }))
        SocketHandler.send_to_all({
            'type': 'sys',
            'message': str(id(self)) + ' has joined',
        })
        SocketHandler.clients.add(self)
        print("server opened")

    def on_close(self):
        SocketHandler.clients.remove(self)
        SocketHandler.send_to_all({
            'type': 'sys',
            'message': str(id(self)) + ' has left',
        })
        print("server closed")

    def on_message(self, message):
        SocketHandler.send_to_all({
            'type': 'user',
            'id': id(self),
            'message': message + " - ps. added message by server",
        })


if __name__ == '__main__':
    app = tornado.web.Application(
        handlers=[
            (r"/", IndexHandler),
            (r"/chat", SocketHandler)
        ],
        debug=True,
        template_path=os.path.join(os.path.dirname(__file__), "templates"),
        static_path=os.path.join(os.path.dirname(__file__), "static")
    )
    app.listen(5000)
    print("Tornado server socket start")
    tornado.ioloop.IOLoop.instance().start()

