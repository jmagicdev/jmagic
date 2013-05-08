package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Illusionary Servant")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class IllusionaryServant extends Card
{
	public static final class Skulking extends EventTriggeredAbility
	{
		public Skulking(GameState state)
		{
			super(state, "When Illusionary Servant becomes the target of a spell or ability, sacrifice it.");
			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			// When Illusionary Servant becomes the target of a spell or
			// ability,
			this.addPattern(new BecomesTheTargetPattern(thisCard));

			// sacrifice it.
			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.PLAYER, You.instance());
			parameters.put(EventType.Parameter.PERMANENT, thisCard);
			this.addEffect(new EventFactory(EventType.SACRIFICE_PERMANENTS, parameters, "Sacrifice Illusionary Servant."));
		}
	}

	public IllusionaryServant(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new Skulking(state));
	}
}
