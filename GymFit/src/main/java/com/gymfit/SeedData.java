package com.gymfit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Component public class SeedData implements CommandLineRunner {
 private final ExerciseRepository exercises; private final RoutineRepository routines; private final AdminRepository admins; private final PasswordEncoder encoder;
 @Value("${gymfit.admin.username}") String username; @Value("${gymfit.admin.password}") String password;
 public SeedData(ExerciseRepository e,RoutineRepository r,AdminRepository a,PasswordEncoder p){exercises=e;routines=r;admins=a;encoder=p;}
 @Transactional public void run(String... args){
 if(admins.count()==0){if(password.isBlank()){password=UUID.randomUUID().toString();System.out.println("GYMFIT · Contraseña inicial de admin: "+password+" · Guardala; no volverá a mostrarse.");} if(password.length()<10)throw new IllegalArgumentException("GYMFIT_ADMIN_PASSWORD debe tener al menos 10 caracteres");admins.save(new AdminAccount(username,encoder.encode(password)));}
 if(exercises.count()>0)return;
 var data=exercises.saveAll(List.of(
 new Exercise("Sentadilla con barra","Piernas","Intermedio","Barra y discos","Apoyá la barra sobre la espalda alta. Descendé con el tronco estable y las rodillas alineadas con los pies. Usá una carga que permita mantener el control."),
 new Exercise("Press de banca","Pecho","Intermedio","Banco y barra","Con los pies apoyados, bajá la barra de forma controlada hacia el pecho y extendé los brazos sin perder la estabilidad."),
 new Exercise("Remo con mancuerna","Espalda","Principiante","Mancuerna y banco","Apoyá una mano en el banco. Llevá la mancuerna hacia la cadera manteniendo la espalda estable; evitá girar el tronco."),
 new Exercise("Press militar","Hombros","Intermedio","Mancuernas","Desde la altura de los hombros, empujá las mancuernas hacia arriba sin arquear la zona lumbar."),
 new Exercise("Curl de bíceps","Brazos","Principiante","Mancuernas","Flexioná los codos sin balancear el cuerpo. Descendé lentamente y mantené los codos cerca del tronco."),
 new Exercise("Plancha abdominal","Core","Principiante","Colchoneta","Apoyá antebrazos y puntas de los pies. Mantené el cuerpo alineado y la respiración fluida. Para ejercicios isométricos, las repeticiones indican segundos."),
 new Exercise("Peso muerto rumano","Piernas","Avanzado","Barra y discos","Llevá la cadera hacia atrás con una ligera flexión de rodillas. Mantené la barra cerca del cuerpo y la espalda estable."),
 new Exercise("Jalón al pecho","Espalda","Principiante","Polea","Llevá la barra hacia la parte superior del pecho, sin balancearte ni tirar detrás de la nuca.")));
 String[] names={"Fuerza · cuerpo completo","Tren superior","Piernas y core"};String[] days={"Lunes","Miércoles","Viernes"};int[][] ids={{0,1,2,5},{1,2,3,4},{0,6,5}};
 for(int i=0;i<3;i++){Routine r=new Routine();r.name=names[i];r.goal="Fuerza general";r.day=days[i];r.difficulty="Intermedio";for(int j:ids[i]){RoutineItem t=new RoutineItem();t.routine=r;t.exercise=data.get(j);t.position=r.items.size();t.sets=3;t.reps=j==5?30:10;t.restSeconds=60;r.items.add(t);}routines.save(r);}
 }
}
