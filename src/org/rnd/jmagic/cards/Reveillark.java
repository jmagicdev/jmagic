package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reveillark")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class Reveillark extends Card
{
	public static final class ReveillarkAbility1 extends EventTriggeredAbility
	{
		public ReveillarkAbility1(GameState state)
		{
			super(state, "When Reveillark leaves the battlefield, return up to two target creature cards with power 2 or less from your graveyard to the battlefield.");
			this.addPattern(whenThisLeavesTheBattlefield());

			SetGenerator legal = Intersect.instance(HasPower.instance(Between.instance(null, 2)), InZone.instance(GraveyardOf.instance(You.instance())));
			Target target = this.addTarget(legal, "up to two target creature cards with power 2 or less from your graveyard");
			target.setNumber(0, 2);

			EventFactory move = new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, "Return up to two target creature cards with power 2 or less from your graveyard to the battlefield.");
			move.parameters.put(EventType.Parameter.CAUSE, This.instance());
			move.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			move.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
			this.addEffect(move);
		}
	}

	public Reveillark(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Reveillark leaves the battlefield, return up to two target
		// creature cards with power 2 or less from your graveyard to the
		// battlefield.
		this.addAbility(new ReveillarkAbility1(state));

		// Evoke (5)(W) (You may cast this spell for its evoke cost. If you do,
		// it's sacrificed when it enters the battlefield.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evoke(state, "(5)(W)"));
	}
}
