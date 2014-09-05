package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Magister Sphinx")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SPHINX})
@ManaCost("4WUB")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.BLACK})
public final class MagisterSphinx extends Card
{
	public static final class Ten extends EventTriggeredAbility
	{
		public Ten(GameState state)
		{
			super(state, "When Magister Sphinx enters the battlefield, target player's life total becomes 10.");

			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Players.instance(), "target player");

			EventFactory factory = new EventFactory(EventType.SET_LIFE, "Target player's life total becomes 10");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(10));
			factory.parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
			this.addEffect(factory);
		}
	}

	public MagisterSphinx(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new Ten(state));
	}
}
