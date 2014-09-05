package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Titan Forge")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class TitanForge extends Card
{
	public static final class TitanForgeAbility0 extends ActivatedAbility
	{
		public TitanForgeAbility0(GameState state)
		{
			super(state, "(3), (T): Put a charge counter on Titan Forge.");
			this.setManaCost(new ManaPool("(3)"));
			this.costsTap = true;
			this.addEffect(putCounters(3, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Titan Forge."));
		}
	}

	public static final class TitanForgeAbility1 extends ActivatedAbility
	{
		public TitanForgeAbility1(GameState state)
		{
			super(state, "(T), Remove three charge counters from Titan Forge: Put a 9/9 colorless Golem artifact creature token onto the battlefield.");
			this.costsTap = true;
			this.addCost(removeCounters(3, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove three charge counters from Titan Forge"));

			CreateTokensFactory factory = new CreateTokensFactory(1, 9, 9, "Put a 9/9 colorless Golem artifact creature token onto the battlefield.");
			factory.setSubTypes(SubType.GOLEM);
			factory.setArtifact();
			this.addEffect(factory.getEventFactory());
		}
	}

	public TitanForge(GameState state)
	{
		super(state);

		// (3), (T): Put a charge counter on Titan Forge.
		this.addAbility(new TitanForgeAbility0(state));

		// (T), Remove three charge counters from Titan Forge: Put a 9/9
		// colorless Golem artifact creature token onto the battlefield.
		this.addAbility(new TitanForgeAbility1(state));
	}
}
