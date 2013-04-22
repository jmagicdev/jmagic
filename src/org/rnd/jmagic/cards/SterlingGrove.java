package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sterling Grove")
@Types({Type.ENCHANTMENT})
@ManaCost("GW")
@Printings({@Printings.Printed(ex = Expansion.INVASION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SterlingGrove extends Card
{
	public static final class SterlingGroveAbility0 extends StaticAbility
	{
		public SterlingGroveAbility0(GameState state)
		{
			super(state, "Other enchantments you control have shroud.");

			SetGenerator yourOtherEnchantments = RelativeComplement.instance(Intersect.instance(HasType.instance(Type.ENCHANTMENT), ControlledBy.instance(You.instance())), This.instance());
			this.addEffectPart(addAbilityToObject(yourOtherEnchantments, org.rnd.jmagic.abilities.keywords.Shroud.class));
		}
	}

	public static final class SterlingGroveAbility1 extends ActivatedAbility
	{
		public SterlingGroveAbility1(GameState state)
		{
			super(state, "(1), Sacrifice Sterling Grove: Search your library for an enchantment card and reveal that card. Shuffle your library, then put the card on top of it.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(sacrificeThis("Sterling Grove"));

			EventFactory effect = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_ON_TOP, "Search your library for an enchantment card and reveal that card. Shuffle your library, then put the card on top of it.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
			effect.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.ENCHANTMENT)));
			this.addEffect(effect);
		}
	}

	public SterlingGrove(GameState state)
	{
		super(state);

		// Other enchantments you control have shroud. (They can't be the
		// targets of spells or abilities.)
		this.addAbility(new SterlingGroveAbility0(state));

		// (1), Sacrifice Sterling Grove: Search your library for an enchantment
		// card and reveal that card. Shuffle your library, then put the card on
		// top of it.
		this.addAbility(new SterlingGroveAbility1(state));
	}
}
