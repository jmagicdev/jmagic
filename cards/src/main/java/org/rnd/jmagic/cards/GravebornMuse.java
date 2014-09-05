package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Graveborn Muse")
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.SPIRIT})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = Legions.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class GravebornMuse extends Card
{
	public static final class ZombieDraw extends EventTriggeredAbility
	{
		public ZombieDraw(GameState state)
		{
			super(state, "At the beginning of your upkeep, you draw X cards and you lose X life, where X is the number of Zombies you control.");

			this.addPattern(atTheBeginningOfYourUpkeep());

			SetGenerator you = ControllerOf.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator X = Count.instance(Intersect.instance(HasSubType.instance(SubType.ZOMBIE), ControlledBy.instance(you)));

			EventType.ParameterMap drawParameters = new EventType.ParameterMap();
			drawParameters.put(EventType.Parameter.CAUSE, This.instance());
			drawParameters.put(EventType.Parameter.NUMBER, X);
			drawParameters.put(EventType.Parameter.PLAYER, you);
			this.addEffect(new EventFactory(EventType.DRAW_CARDS, drawParameters, "You draw X cards"));

			this.addEffect(loseLife(You.instance(), X, "and you lose X life, where X is the number of Zombies you control."));
		}
	}

	public GravebornMuse(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new ZombieDraw(state));
	}
}
