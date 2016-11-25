import model.*;
import java.util.*;

public final class QuickStartStrategyState extends StrategyState {
    private static final double WAYPOINT_RADIUS = 100.0D;
    private static final double LOW_HP_FACTOR = 0.25D;

    /**
     * Ключевые точки для каждой линии, позволяющие упростить управление перемещением волшебника.
     * <p>
     * Если всё хорошо, двигаемся к следующей точке и атакуем противников.
     * Если осталось мало жизненной энергии, отступаем к предыдущей точке.
     */
    private ArrayList<MyPoint> waypoints;

    /**
     * Основной метод стратегии, осуществляющий управление волшебником.
     * Вызывается каждый тик для каждого волшебника.
     *
     * @param self  Волшебник, которым данный метод будет осуществлять управление.
     * @param world Текущее состояние мира.
     * @param game  Различные игровые константы.
     * @param move  Результатом работы метода является изменение полей данного объекта.
     */

    public void move(Wizard self, World world, Game game, Move move) {
        initializeStrategy(self, game);
        initializeTick(self, world, game, move);

        // Постоянно двигаемся из-стороны в сторону, чтобы по нам было сложнее попасть.
        // Считаете, что сможете придумать более эффективный алгоритм уклонения? Попробуйте! ;)
        move.setStrafeSpeed(random.nextBoolean() ? game.getWizardStrafeSpeed() : -game.getWizardStrafeSpeed());

        // Если осталось мало жизненной энергии, отступаем к предыдущей ключевой точке на линии.
        if (self.getLife() < self.getMaxLife() * LOW_HP_FACTOR) {
            MyPoint previousWaypoint = getPreviousWaypoint();
            MoveUtil.goTo(self, game, move, previousWaypoint.getX(), previousWaypoint.getY());
            return;
        }

        LivingUnit nearestTarget = SearchUtil.getNearestTarget(self, world);

        // Если видим противника ...
        if (nearestTarget != null) {
            double distance = self.getDistanceTo(nearestTarget);

            // ... и он в пределах досягаемости наших заклинаний, ...
            if (distance <= self.getCastRange()) {
                double angle = self.getAngleTo(nearestTarget);

                // ... то поворачиваемся к цели.
                move.setTurn(angle);

                // Если цель перед нами, ...
                if (StrictMath.abs(angle) < game.getStaffSector() / 2.0D) {
                    // ... то атакуем.
                    move.setAction(ActionType.MAGIC_MISSILE);
                    move.setCastAngle(angle);
                    move.setMinCastDistance(distance - nearestTarget.getRadius() + game.getMagicMissileRadius());
                }

                return;
            }
        }
        // Если нет других действий, просто продвигаемся вперёд.
        MyPoint nextWayPoint = getNextWaypoint();
        MoveUtil.goTo(self, game, move, nextWayPoint.getX(), nextWayPoint.getY());
    }
    /**
     * Инциализируем стратегию.
     * <p>
     * Для этих целей обычно можно использовать конструктор, однако в данном случае мы хотим инициализировать генератор
     * случайных чисел значением, полученным от симулятора игры.
     */
    private void initializeStrategy(Wizard self, Game game) {
        if (random == null) {
            random = new Random(game.getRandomSeed());
            waypoints = RouteUtil.buildQuickStart(self, game);
        }
    }

    private MyPoint getNextWaypoint() {
        int lastWaypointIndex = waypoints.size() - 1;
        MyPoint lastWaypoint = waypoints.get(lastWaypointIndex);

        for (int waypointIndex = 0; waypointIndex < lastWaypointIndex; ++waypointIndex) {
            MyPoint waypoint = waypoints.get(waypointIndex);
            if (waypoint.getDistanceTo(self) <= WAYPOINT_RADIUS) {
                return waypoints.get(waypointIndex + 1);
            }
            if (lastWaypoint.getDistanceTo(waypoint) < lastWaypoint.getDistanceTo(self)) {
                return waypoint;
            }
        }
        return lastWaypoint;
    }
    private MyPoint getPreviousWaypoint() {
        MyPoint firstWaypoint = waypoints.get(0);

        for (int waypointIndex = waypoints.size() - 1; waypointIndex > 0; --waypointIndex) {
            MyPoint waypoint = waypoints.get(waypointIndex);
            if (waypoint.getDistanceTo(self) <= WAYPOINT_RADIUS) {
                return waypoints.get(waypointIndex - 1);
            }
            if (firstWaypoint.getDistanceTo(waypoint) < firstWaypoint.getDistanceTo(self)) {
                return waypoint;
            }
        }
        return firstWaypoint;
    }
}
