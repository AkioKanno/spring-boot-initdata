# 概要  
Spring Boot で import.sql を使わない初期データ投入のサンプルコードです。  
通常はクラスパスの通ったディレクトリに import.sql を配置することで、アプリケーション起動時に初期データの投入ができます。  
  
こちらの方法では import.sql を使わず Interceptor を実装し、アプリケーション起動時に DDL が実行された場合のみでデータ投入を行います。

# 仕組み  
## 初期データ投入(Repository実装)  
データは SQL を作成するのではなく、Repository の save を行う(テーブルの定義は Entity で)。  
JPAを使っているのであれば、通常の Repository を使ったデータ保存と同じ内容を実装していく。  


### Entity と Repository  
今回は sample なので、シンプルに id, name, age の３カラムだけをもつテーブルを定義  

```
@Entity(name = "user")
@EntityListeners(value = AuditingEntityListener.class)
public class UserEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name = "name", nullable = false, length = 255)
    public String name;

    @Column(name = "age", nullable = false)
    public int age;
}


public interface UserRepository extends JpaRepository<UserEntity, Integer> {　　
}
```
### データ投入の実装  
通常のデータ保存の実装と同様の処理を実装するだけである。  

```
public class InputData {

    @Autowired
    UserRepository repository;

    @PostConstruct
    public void insertUser() {
        UserEntity entity = new UserEntity();
        entity.name = "testUser";
        entity.age = 18;
        this.repository.save(entity);
    }
}

```

## Interceptor の実装
Spring Boot では @EnableWebMvc の利用はできないため、WebMvcConfigureAdapter を実装  
@Bean のアノテーションを付与し、起動時に先ほどの初期データ投入のコーをを実行させる
```
@Configuration
public class ConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String PROPERTY_DDL_AUTO;

    @Bean
    public InputData inputData() {
        return ("create".equals(this.PROPERTY_DDL_AUTO)) ? new InputData() : null;
    }
}

```
