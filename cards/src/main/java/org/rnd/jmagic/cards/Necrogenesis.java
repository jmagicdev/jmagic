package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Necrogenesis")
@Types({Type.ENCHANTMENT})
@ManaCost("BG")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class Necrogenesis extends Card
{
	public static final class NecrogenesisAbility0 extends ActivatedAbility
	{
		public NecrogenesisAbility0(GameState state)
		{
			super(state, "(2): Exile target creature card from a graveyard. Put a 1/1 green Saproling creature token onto the battlefield.");
			this.setManaCost(new ManaPool("(2)"));

			SetGenerator targetable = Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(GraveyardOf.instance(Players.instance())));
			SetGenerator target = targetedBy(this.addTarget(targetable, "target creature card from a graveyard"));

			this.addEffect(exile(target, "Exile target creature card from a graveyard."));

			CreateTokensFactory f = new CreateTokensFactory(1, 1, 1, "Put a 1/1 green Saproling creature token onto the battlefield.");
			f.setColors(Color.GREEN);
			f.setSubTypes(SubType.SAPROLING);
			this.addEffect(f.getEventFactory());
		}
	}

	public Necrogenesis(GameState state)
	{
		super(state);

		// (2): Exile target creature card from a graveyard. Put a 1/1 green
		// Saproling creature token onto the battlefield.
		this.addAbility(new NecrogenesisAbility0(state));
	}
}
