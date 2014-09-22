package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Righteous Authority")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("3WU")
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class RighteousAuthority extends Card
{
	public static final class RighteousAuthorityAbility1 extends StaticAbility
	{
		public RighteousAuthorityAbility1(GameState state)
		{
			super(state, "Enchanted creature gets +1/+1 for each card in its controller's hand.");

			SetGenerator enchanted = EnchantedBy.instance(This.instance());
			SetGenerator N = Count.instance(InZone.instance(HandOf.instance(ControllerOf.instance(enchanted))));

			this.addEffectPart(modifyPowerAndToughness(enchanted, N, N));
		}
	}

	public static final class RighteousAuthorityAbility2 extends EventTriggeredAbility
	{
		public RighteousAuthorityAbility2(GameState state)
		{
			super(state, "At the beginning of the draw step of enchanted creature's controller, that player draws an additional card.");

			SetGenerator enchanted = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator controller = ControllerOf.instance(enchanted);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, DrawStepOf.instance(controller));
			this.addPattern(pattern);

			this.addEffect(drawCards(controller, 1, "That player draws an additional card."));
		}
	}

	public RighteousAuthority(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature gets +1/+1 for each card in its controller's hand.
		this.addAbility(new RighteousAuthorityAbility1(state));

		// At the beginning of the draw step of enchanted creature's controller,
		// that player draws an additional card.
		this.addAbility(new RighteousAuthorityAbility2(state));
	}
}
