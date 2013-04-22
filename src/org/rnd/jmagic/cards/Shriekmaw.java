package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shriekmaw")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.LORWYN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Shriekmaw extends Card
{
	public static final class ETBTerror extends EventTriggeredAbility
	{
		public ETBTerror(GameState state)
		{
			super(state, "When Shriekmaw enters the battlefield, destroy target nonartifact, nonblack creature.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator nonartifactCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasType.instance(Type.ARTIFACT));
			SetGenerator nonartifactNonblackCreatures = RelativeComplement.instance(nonartifactCreatures, HasColor.instance(Color.BLACK));
			Target target = this.addTarget(nonartifactNonblackCreatures, "target nonartifact, nonblack creature");
			this.addEffects(bury(this, targetedBy(target), "Destroy target nonartifact, nonblack creature. It can't be regenerated."));
		}
	}

	public Shriekmaw(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Fear
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fear(state));

		// When Shriekmaw enters the battlefield, destroy target nonartifact,
		// nonblack creature.
		this.addAbility(new ETBTerror(state));

		// Evoke (1)(B)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evoke(state, "(1)(B)"));
	}
}
