package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Conversion Chamber")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class ConversionChamber extends Card
{
	public static final class ConversionChamberAbility0 extends ActivatedAbility
	{
		public ConversionChamberAbility0(GameState state)
		{
			super(state, "(2), (T): Exile target artifact card from a graveyard. Put a charge counter on Conversion Chamber.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.ARTIFACT), InZone.instance(GraveyardOf.instance(Players.instance()))), "target artifact card in a graveyard"));
			this.addEffect(exile(target, "Exile target artifact card from a graveyard."));
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Conversion Chamber."));
		}
	}

	public static final class ConversionChamberAbility1 extends ActivatedAbility
	{
		public ConversionChamberAbility1(GameState state)
		{
			super(state, "(2), (T), Remove a charge counter from Conversion Chamber: Put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Conversion Chamber"));

			CreateTokensFactory factory = new CreateTokensFactory(1, 3, 3, "Put a 3/3 colorless Golem artifact creature token onto the battlefield.");
			factory.setSubTypes(SubType.GOLEM);
			factory.setArtifact();
			this.addEffect(factory.getEventFactory());
		}
	}

	public ConversionChamber(GameState state)
	{
		super(state);

		// (2), (T): Exile target artifact card from a graveyard. Put a charge
		// counter on Conversion Chamber.
		this.addAbility(new ConversionChamberAbility0(state));

		// (2), (T), Remove a charge counter from Conversion Chamber: Put a 3/3
		// colorless Golem artifact creature token onto the battlefield.
		this.addAbility(new ConversionChamberAbility1(state));
	}
}
