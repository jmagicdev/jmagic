package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Massacre Wurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("3BBB")
@ColorIdentity({Color.BLACK})
public final class MassacreWurm extends Card
{
	public static final class MassacreWurmAbility0 extends EventTriggeredAbility
	{
		public MassacreWurmAbility0(GameState state)
		{
			super(state, "When Massacre Wurm enters the battlefield, creatures your opponents control get -2/-2 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(ptChangeUntilEndOfTurn(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), -2, -2, "Creatures your opponents control get -2/-2 until end of turn."));
		}
	}

	public static final class MassacreWurmAbility1 extends EventTriggeredAbility
	{
		public MassacreWurmAbility1(GameState state)
		{
			super(state, "Whenever a creature an opponent controls dies, that player loses 2 life.");
			this.addPattern(new org.rnd.jmagic.engine.patterns.SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), true));

			SetGenerator thatPlayer = ControllerOf.instance(OldObjectOf.instance(TriggerZoneChange.instance(This.instance())));

			this.addEffect(loseLife(thatPlayer, 2, "That player loses 2 life."));
		}
	}

	public MassacreWurm(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(5);

		// When Massacre Wurm enters the battlefield, creatures your opponents
		// control get -2/-2 until end of turn.
		this.addAbility(new MassacreWurmAbility0(state));

		// Whenever a creature an opponent controls is put into a graveyard from
		// the battlefield, that player loses 2 life.
		this.addAbility(new MassacreWurmAbility1(state));
	}
}
