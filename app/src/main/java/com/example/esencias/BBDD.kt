package com.example.esencias
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BBDD(context: Context) : SQLiteOpenHelper(context, "esenciasBBDD.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        val tablaUsuario = """
            CREATE TABLE Usuario (
                correo TEXT PRIMARY KEY,
                nombre TEXT NOT NULL UNIQUE,
                pass TEXT NOT NULL,
                direccion TEXT,
                fotoPerfil TEXT,
                privilegios TEXT,
                tlfn TEXT
            );
        """

        val tablaTarjeta = """
            CREATE TABLE Tarjeta (
                nCuenta TEXT PRIMARY KEY,
                caducidad TEXT,
                codSeguridad TEXT,
                tipo TEXT
            );
        """

        val tablaUsuario_Tarjeta = """
            CREATE TABLE Usuario_Tarjeta (
                correo TEXT,
                nCuenta TEXT,
                PRIMARY KEY (correo, nCuenta),
                FOREIGN KEY (correo) REFERENCES Usuario(correo),
                FOREIGN KEY (nCuenta) REFERENCES Tarjeta(nCuenta)
            );
        """

        val tablaCesta ="""
        CREATE TABLE Cesta (
            idCesta INTEGER PRIMARY KEY AUTOINCREMENT,
            correo TEXT NOT NULL UNIQUE,
            FOREIGN KEY (correo) REFERENCES Usuario(correo)
        );
    """

        val tablaProducto = """
            CREATE TABLE Producto (
                idProducto INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                precio REAL,
                descripcion TEXT,
                informacion TEXT,
                imagen TEXT
            );
        """

        val tablaCestaProducto = """
            CREATE TABLE Cesta_Producto (
                idCesta INTEGER,
                idProducto INTEGER,
                PRIMARY KEY (idCesta, idProducto),
                FOREIGN KEY (idCesta) REFERENCES Cesta(idCesta),
                FOREIGN KEY (idProducto) REFERENCES Producto(idProducto)
            );
        """

        val tablaPedido = """
            CREATE TABLE Pedido (
                idPedido INTEGER PRIMARY KEY AUTOINCREMENT,
                correo TEXT NOT NULL,
                estado TEXT NOT NULL,
                fecha TEXT NOT NULL,
                importe REAL NOT NULL,
                FOREIGN KEY (correo) REFERENCES Usuario(correo)
            );
        """

        val tablaPedidosProductos = """
        CREATE TABLE Pedido_Producto (
            idPedido INTEGER NOT NULL,
            idProducto INTEGER NOT NULL,
            PRIMARY KEY (idPedido, idProducto),
            FOREIGN KEY (idPedido) REFERENCES Pedido(idPedido),
            FOREIGN KEY (idProducto) REFERENCES Producto(idProducto)
        );
        """
        val tablaCursos = """
        CREATE TABLE Curso (
            idProducto INTEGER PRIMARY KEY,
            plazasDisponibles INTEGER NOT NULL,
            plazasMaximas INTEGER NOT NULL,
            FOREIGN KEY (idProducto) REFERENCES Producto(idProducto) ON DELETE CASCADE
        );
        """

        val tablaVelas = """
        CREATE TABLE Vela (
            idProducto INTEGER PRIMARY KEY,
            tamano TEXT NOT NULL,
            aromas TEXT NOT NULL,
            FOREIGN KEY (idProducto) REFERENCES Producto(idProducto) ON DELETE CASCADE
        );
        """

        val insertVelas = """
            INSERT INTO Producto  (precio,descripcion,informacion, nombre, imagen)
            VALUES
            (9.99,'Disfruta de nuestra vela aromática con esencia de lavanda, que llena el ambiente de serenidad y frescura. Perfecta para acompañarte en momentos de relajación, ya sea mientras lees, meditas o simplemente te desconectas del mundo. Su delicado aroma floral transforma cualquier espacio en un remanso de paz, invitándote a respirar profundo y dejar atrás el estrés del día. ¡Haz de cada instante un oasis de calma y bienestar!','Todas nuestras velas y todos sus componentes, independientemente de su aroma, son 100% veganas y cruelty free.
Ingredientes: cera 100% de soja sin aditivos, mecha de algodón ecológico, aceites esenciales y esencias aromáticas de calidad premium, certificados por la IFRA.

260 gr / 450 gr

Horas de encendido: 60/90 horas', 'Vela con esencia de lavanda', 'https://i.pinimg.com/originals/66/82/d3/6682d3b1da1a78b3843b951613be0288.jpg'),
           
            (12.99,'Descubre nuestra vela perfumada de vainilla, diseñada para envolver tus sentidos en una cálida y dulce fragancia. Ideal para crear un ambiente acogedor en tus momentos de descanso o reflexión. Su aroma envolvente transforma cualquier espacio en un refugio de confort y tranquilidad, perfecto para tardes de lectura, un baño relajante o una cena íntima. ¡Llena tu hogar de magia y serenidad con cada chispa!','Todas nuestras velas y todos sus componentes, independientemente de su aroma, son 100% veganas y cruelty free.
Ingredientes: cera 100% de soja sin aditivos, mecha de algodón ecológico, aceites esenciales y esencias aromáticas de calidad premium, certificados por la IFRA.

260 gr / 450 gr

Horas de encendido: 60/90 horas', 'Vela perfumada de vainilla', 'https://i.pinimg.com/originals/66/82/d3/6682d3b1da1a78b3843b951613be0288.jpg'),
            
            (15.99,'Sumérgete en la elegancia de nuestra vela con fragancia de rosa, que llena tus espacios con un aroma floral y sofisticado. Perfecta para crear un ambiente romántico o para momentos de autocuidado, su delicado perfume inspira calma y belleza. Ya sea acompañando una cena especial, un baño relajante o una tarde tranquila, esta vela transforma cualquier rincón en un oasis de encanto y armonía. ¡Añade un toque de refinamiento y serenidad a tu día!','Todas nuestras velas y todos sus componentes, independientemente de su aroma, son 100% veganas y cruelty free.
Ingredientes: cera 100% de soja sin aditivos, mecha de algodón ecológico, aceites esenciales y esencias aromáticas de calidad premium, certificados por la IFRA.

260 gr / 450 gr

Horas de encendido: 60/90 horas', 'Vela con fragancia de rosa', 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmejorconsalud.as.com%2Fwp-content%2Fuploads%2F2019%2F05%2Fhacer-velas-arom%25C3%25A1ticas-de-gel.jpg&f=1&nofb=1&ipt=9520c5f7bc035d20b99fd4c49ec9007622ff70f2c18c619b4e3b4f762646794c&ipo=images'),
            
            (10.99,'Déjate llevar por el encanto exótico de nuestra vela de coco con aroma tropical, que trae a tu hogar la calidez y frescura de un paraíso lejano. Perfecta para momentos de relajación o para añadir un toque vibrante a tus espacios, su fragancia te transporta a playas de arena blanca y brisas tropicales. Ya sea para una tarde de descanso, una cena especial o simplemente para disfrutar el momento, esta vela convierte cualquier ambiente en un rincón de verano eterno. ¡Haz de cada día una escapada tropical!','Todas nuestras velas y todos sus componentes, independientemente de su aroma, son 100% veganas y cruelty free.
Ingredientes: cera 100% de soja sin aditivos, mecha de algodón ecológico, aceites esenciales y esencias aromáticas de calidad premium, certificados por la IFRA.

260 gr / 450 gr

Horas de encendido: 60/90 horas', 'Vela de coco con aroma tropical', 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fmejorconsalud.as.com%2Fwp-content%2Fuploads%2F2019%2F05%2Fhacer-velas-arom%25C3%25A1ticas-de-gel.jpg&f=1&nofb=1&ipt=9520c5f7bc035d20b99fd4c49ec9007622ff70f2c18c619b4e3b4f762646794c&ipo=images'),
           
            (14.99,'Enciende la calidez de nuestra vela de canela y especias, que envuelve tu hogar en un abrazo aromático lleno de confort. Su fragancia rica y especiada es ideal para crear un ambiente acogedor en días frescos o para añadir un toque especial a tus momentos de relajación. Perfecta para acompañar tardes de lectura, reuniones íntimas o una pausa con tu bebida favorita, esta vela transforma cualquier espacio en un refugio cálido y nostálgico. ¡Haz que cada instante sea único con su magia especiada!','Todas nuestras velas y todos sus componentes, independientemente de su aroma, son 100% veganas y cruelty free.
Ingredientes: cera 100% de soja sin aditivos, mecha de algodón ecológico, aceites esenciales y esencias aromáticas de calidad premium, certificados por la IFRA.

260 gr / 450 gr

Horas de encendido: 60/90 horas', 'Vela de canela y especias', 'https://cdn.shopify.com/s/files/1/0732/7734/1971/files/R0M__VELA_RUSTIQUE_NO_AROMATICA_5.5X5.5_MARFIL_c.jpg?v=1686316324'),
            
            (15.99,'Disfruta de la pureza y frescura de nuestra vela con fragancia de algodón, que llena tus espacios de una sensación de limpieza y tranquilidad. Ideal para crear un ambiente sereno y relajante, su delicado aroma evoca la suavidad de las sábanas recién lavadas y la brisa fresca de un día soleado. Perfecta para tus momentos de descanso, meditación o simplemente para renovar el ambiente, esta vela convierte cualquier rincón en un oasis de calma y frescura. ¡Lleva la esencia de lo simple y lo puro a tu hogar!','Todas nuestras velas y todos sus componentes, independientemente de su aroma, son 100% veganas y cruelty free.
Ingredientes: cera 100% de soja sin aditivos, mecha de algodón ecológico, aceites esenciales y esencias aromáticas de calidad premium, certificados por la IFRA.

260 gr / 450 gr

Horas de encendido: 60/90 horas', 'Vela con fragancia de algodón', 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2Foriginals%2F7d%2Ff4%2F1a%2F7df41a57de92a482e68e279b07726563.png&f=1&nofb=1&ipt=2662a365556b941c61b1c75e572e002e7e40475172b6e8bdca758718da691f18&ipo=images');

        """

        val insertVelas2 = """
            
            INSERT INTO Vela (idProducto, tamano, aromas)
            VALUES
            (1, 'Pequeña', 'Lavanda'),
            (2, 'Mediana', 'Vainilla'),
            (3, 'Grande', 'Rosa'),
            (4, 'Pequeña', 'Coco'),
            (5, 'Mediana', 'Canela'),
            (6, 'Pequeña', 'Coco');

        """
        val insertAdmin = """
            INSERT INTO Usuario(correo,nombre,pass,privilegios) VALUES("","","","admin")
        """

        val insertCurso = """
            INSERT INTO Producto  (precio,descripcion,informacion, nombre, imagen)
            VALUES
            (39.99,'Disfruta de nuestros cursos de creación de velas dispuestos para todo el mundo de la mano de nuestros trabajadores expertos. Además, no es necesario tener ningún tipo de conocimiento previo, tan solo tener ganas de aprender como nosotros de enseñarte. También, tendrás la oportunidad de obtener una vela creada por tí realizada con la guía de nuestros profesores.'
            ,'Este curso  tiene una duración de 3 horas en el que se dispondrá de un profesor que explique y guíe durante el proceso de hacer una vela. Además, acompañará a cada uno para llevar a cabo su propia vela, sirviendo de una actividad artesanal aromática relajante. 
            
Los cursos se darán todos los martes y jueves salvo festivos y vacaciones de 17h a 20h. 

Las clases tienen una capacidad de 10 personas y se registrarán por orden de llegada. 

Precio: 39,99€', 'Curso: Crea tu vela', 
            'https://www.velastierradealba.com/wp-content/uploads/2017/11/aprende-a-hacer-velas-gratis.-curso-online.jpg');

        """

        val insertCurso2 = """ 
            
            INSERT INTO Curso (idProducto, plazasDisponibles, plazasMaximas)
            VALUES
            (7, 10, 10);
            
        """


        db.execSQL(tablaUsuario)
        db.execSQL(tablaTarjeta)
        db.execSQL(tablaUsuario_Tarjeta)
        db.execSQL(tablaCesta)
        db.execSQL(tablaProducto)
        db.execSQL(tablaCestaProducto)
        db.execSQL(tablaPedido)
        db.execSQL(tablaPedidosProductos)
        db.execSQL(tablaCursos)
        db.execSQL(tablaVelas)
        db.execSQL(insertVelas)
        db.execSQL(insertVelas2)
        db.execSQL(insertAdmin)
        db.execSQL(insertCurso)
        db.execSQL(insertCurso2)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Usuario_Tarjeta")
        db.execSQL("DROP TABLE IF EXISTS Cesta_Producto")
        db.execSQL("DROP TABLE IF EXISTS Pedido_Producto")
        db.execSQL("DROP TABLE IF EXISTS Tarjeta")
        db.execSQL("DROP TABLE IF EXISTS Cesta")
        db.execSQL("DROP TABLE IF EXISTS Producto")
        db.execSQL("DROP TABLE IF EXISTS Pedido")
        db.execSQL("DROP TABLE IF EXISTS Curso")
        db.execSQL("DROP TABLE IF EXISTS Vela")
        db.execSQL("DROP TABLE IF EXISTS Usuario")
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("PRAGMA foreign_keys=ON;")
    }

    /**
     * Intenta la insercion de un usuario,
     * Devuelve un Int:
     *  0: Insercion correcta
     *  1: Correo duplicado en bbdd
     *  2: Nombre duplicado en bbdd
     * */
    fun insertarUsuario(correo:String,nombre:String, pass:String, direccion:String?,fotoPerfil:String?, privilegios:String?, tlfn:String?):Int{

        val db=this.writableDatabase
        val values= ContentValues().apply {
            put("correo",correo)
            put("nombre",nombre)
            put("pass",pass)
            put("direccion",direccion)
            put("fotoPerfil",fotoPerfil)
            put("privilegios",privilegios)
            put("tlfn",tlfn)
        }
        try{
            db.insertOrThrow("usuario",null,values)
        }catch (e:SQLiteConstraintException){

            if(e.message?.contains("UNIQUE constraint failed: Usuario.correo")==true) return 1
            if(e.message?.contains("UNIQUE constraint failed: Usuario.nombre")==true) return 2

        }finally{
            db.close()
        }
        return 0
    }
    /**
     * Busca un usuario por su nombre o correo.
     * Devuelve el nombre encontrado en BBDD, si no se encuentra, devolverá null.
     * */
    fun encontrarUsuario(nombre:String?):String?{
        val db = this.readableDatabase
        val query = "SELECT nombre FROM Usuario WHERE nombre = ? OR correo = ?"
        val cursor = db.rawQuery(query, arrayOf(nombre,nombre))
        var ret:String?=null

        if(cursor.moveToFirst()) ret=cursor.getString(0)

        cursor.close()
        db.close()

        return ret
    }
    /**
     * Dado un nombre y una contraseña, comprueba en BBDD
     * Devuelve:
     *  True: la contraseña es correcta
     *  False: la contraseña es incorrecta
     * */
    fun verificarUsuario(nombre: String, pass: String):Array<Boolean>{

        val db=this.readableDatabase
        val query="SELECT nombre, pass, privilegios FROM Usuario WHERE nombre = ?"
        val cursor = db.rawQuery(query, arrayOf(nombre))
        val ret = arrayOf(false, false)

        if(cursor.moveToFirst()){
            if(cursor.getString(1) == pass) ret[0]=true
            if(cursor.getString(2) == "admin") ret[1]=true
        }
        db.close()
        cursor.close()
        return ret
    }

    fun listaVelas(): List<Vela> {
        val listaVelas = mutableListOf<Vela>()
        val db = this.readableDatabase

        val query = """
            SELECT
                Producto.nombre AS nombre,
                Producto.precio AS precio,
                Producto.imagen AS imagen,
                Producto.descripcion AS descripcion,
                Producto.informacion AS informacion,
                Vela.tamano AS tamano,
                Vela.aromas AS aromas
            FROM Producto
            INNER JOIN Vela ON Producto.idProducto = Vela.idProducto;
        """

        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio")).toString()
                val imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"))
                val informacion = cursor.getString(cursor.getColumnIndexOrThrow("informacion"))
                val tamano = cursor.getString(cursor.getColumnIndexOrThrow("tamano"))
                val aromas = cursor.getString(cursor.getColumnIndexOrThrow("aromas"))
                listaVelas.add(
                    Vela(
                        nombre = nombre,
                        precio = precio,
                        descripcion = descripcion,
                        informacion = informacion,
                        imagen = imagen,
                        tamano = tamano,
                        aromas = aromas,
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaVelas
    }
    fun insertarVela(nombre:String, precio: Double, descripcion:String, informacion:String, imagen:String,tamano:String?,aromas:String?){

        val db=this.writableDatabase

        val values= ContentValues().apply {
            put("nombre",nombre)
            put("precio",precio)
            put("descripcion",descripcion)
            put("informacion",informacion)
            put("imagen",imagen)
        }

        val id=db.insertOrThrow("Producto",null,values)

        val values2= ContentValues().apply {
            put("idProducto",id)
            put("tamano",tamano)
            put("aromas",aromas)
        }
        db.insertOrThrow("Vela",null,values2)

        db.close()
    }
    fun eliminarVela(codigo:String){

        val db=this.writableDatabase
        db.delete("Vela", "idProducto=?", arrayOf(codigo.toString()))
        db.delete("Producto", "idProducto=?", arrayOf(codigo.toString()))

    }

    fun modificarVela(codigo:String,nombre:String, precio: Double, descripcion:String, informacion:String, imagen:String,tamano:String?, aromas:String?){
        val db = this.writableDatabase


        val values = ContentValues().apply {
            if(nombre!=null && nombre!="") put("nombre",nombre)
            if(precio!=null && precio!=-1.0) put("precio",precio)
            if(descripcion!=null && descripcion!="") put("descripcion",descripcion)
            if(informacion!=null && informacion!="") put("informacion",informacion)
            if(imagen!=null && imagen!="") put("imagen",imagen)
        }

        val values2 = ContentValues().apply {
            if(tamano!=null && tamano!="") put("tamano",tamano)
            if(aromas!=null && aromas!="") put("aromas",aromas)
        }

        if (values.size() > 0) db.update("Producto", values, "idProducto=?", arrayOf(codigo))
        if (values2.size() > 0) db.update("Vela", values2, "idProducto=?", arrayOf(codigo))

    }

    // Métodos para insertar, modificar y eliminar curso

    fun insertarCurso(nombre:String, precio: Double, descripcion:String, informacion:String, imagen:String,plazasDisponibles:Int?,plazasMaximas:Int?){

        val db=this.writableDatabase

        val values= ContentValues().apply {
            put("nombre",nombre)
            put("precio",precio)
            put("descripcion",descripcion)
            put("informacion",informacion)
            put("imagen",imagen)
        }

        val id=db.insertOrThrow("Producto",null,values)

        val values2= ContentValues().apply {
            put("idProducto",id)
            put("plazasDisponibles",plazasDisponibles)
            put("plazasMaximas",plazasMaximas)
        }
        db.insertOrThrow("Curso",null,values2)

        db.close()
    }
    fun eliminarCurso(codigo:String){

        val db=this.writableDatabase
        db.delete("Curso", "idProducto=?", arrayOf(codigo.toString()))
        db.delete("Producto", "idProducto=?", arrayOf(codigo.toString()))

    }

    fun modificarCurso(codigo:String,nombre:String, precio: Double, descripcion:String, informacion:String, imagen:String, plazasDisponibles: Int?, plazasMaximas: Int?){
        val db = this.writableDatabase


        val values = ContentValues().apply {
            if(nombre!=null && nombre!="") put("nombre",nombre)
            if(precio!=null && precio!=-1.0) put("precio",precio)
            if(descripcion!=null && descripcion!="") put("descripcion",descripcion)
            if(informacion!=null && informacion!="") put("informacion",informacion)
            if(imagen!=null && imagen!="") put("imagen",imagen)
        }

        val values2 = ContentValues().apply {
            if(plazasDisponibles!=null && plazasDisponibles!=0) put("plazasDisponibles",plazasDisponibles)
            if(plazasMaximas!=null && plazasMaximas!=0)put("plazasMaximas",plazasMaximas)
        }

        if (values.size() > 0) db.update("Producto", values, "idProducto=?", arrayOf(codigo))
        if (values2.size() > 0) db.update("Curso", values2, "idProducto=?", arrayOf(codigo))

    }

    // Método para sacar la información del curso

    fun getCurso(): Curso? {
        val db = readableDatabase
        // Consulta SQL con INNER JOIN entre Curso y Producto
        val query = """
        SELECT 
            Producto.idProducto,
            Producto.nombre,
            Producto.precio,
            Producto.descripcion,
            Producto.informacion,
            Producto.imagen,
            Curso.plazasDisponibles,
            Curso.plazasMaximas
        FROM Curso
        INNER JOIN Producto ON Curso.idProducto = Producto.idProducto
        LIMIT 1;
    """

        val cursor = db.rawQuery(query, null)

        return if (cursor.moveToFirst()) {
            // Crear un objeto Curso con los datos recuperados
            val curso = Curso(
                idProducto = cursor.getInt(cursor.getColumnIndexOrThrow("idProducto")),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre")),
                precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio")),
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion")),
                informacion = cursor.getString(cursor.getColumnIndexOrThrow("informacion")),
                imagen = cursor.getString(cursor.getColumnIndexOrThrow("imagen")),
                plazasDisponibles = cursor.getInt(cursor.getColumnIndexOrThrow("plazasDisponibles")),
                plazasMaximas = cursor.getInt(cursor.getColumnIndexOrThrow("plazasMaximas"))
            )
            cursor.close()
            curso
        } else {
            cursor.close()
            null
        }
    }
}