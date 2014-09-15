package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Siege Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("5RR")
@ColorIdentity({Color.RED})
public final class SiegeDragon extends Card
{
	public static final class SiegeDragonAbility1 extends EventTriggeredAbility
	{
		public SiegeDragonAbility1(GameState state)
		{
			super(state, "When Siege Dragon enters the battlefield, destroy all Walls your opponents control.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator walls = HasSubType.instance(SubType.WALL);
			SetGenerator enemyWalls = Intersect.instance(ControlledBy.instance(OpponentsOf.instance(You.instance())), walls);
			this.addEffect(destroy(enemyWalls, "Destroy all Walls your opponents control."));
		}
	}

	public static final class SiegeDragonAbility2 extends EventTriggeredAbility
	{
		public SiegeDragonAbility2(GameState state)
		{
			super(state, "Whenever Siege Dragon attacks, if defending player controls no Walls, it deals 2 damage to each creature without flying that player controls.");
			this.addPattern(whenThisAttacks());

			SetGenerator walls = HasSubType.instance(SubType.WALL);
			SetGenerator enemyWalls = Intersect.instance(ControlledBy.instance(DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS)), walls);
			SetGenerator noEnemyWalls = Not.instance(enemyWalls);

			SetGenerator thatPlayer = EventParameter.instance(TriggerEvent.instance(This.instance()), EventType.Parameter.PLAYER);
			SetGenerator theirGuys = ControlledBy.instance(thatPlayer);
			SetGenerator nonflyers = RelativeComplement.instance(theirGuys, HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			EventFactory damage = permanentDealDamage(2, nonflyers, "Siege Dragon deals 2 damage to each creature without flying that player controls.");

			this.addEffect(ifThen(noEnemyWalls, damage, "If defending player controls no Walls, Siege Dragon deals 2 damage to each creature without flying that player controls."));
		}
	}

	public SiegeDragon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Siege Dragon enters the battlefield, destroy all Walls your
		// opponents control.
		this.addAbility(new SiegeDragonAbility1(state));

		// Whenever Siege Dragon attacks, if defending player controls no Walls,
		// it deals 2 damage to each creature without flying that player
		// controls.
		this.addAbility(new SiegeDragonAbility2(state));
	}
}
