package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thopter Assembly")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.THOPTER})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({})
public final class ThopterAssembly extends Card
{
	public static final class ThopterAssemblyAbility1 extends EventTriggeredAbility
	{
		public ThopterAssemblyAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, if you control no Thopters other than Thopter Assembly, return Thopter Assembly to its owner's hand and put five 1/1 colorless Thopter artifact creature tokens with flying onto the battlefield.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.interveningIf = Not.instance(RelativeComplement.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.THOPTER)), ABILITY_SOURCE_OF_THIS));
			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Thopter Assembly to its owner's hand."));

			CreateTokensFactory factory = new CreateTokensFactory(5, 1, 1, "Put five 1/1 colorless Thopter artifact creature tokens with flying onto the battlefield.");
			factory.setSubTypes(SubType.THOPTER);
			factory.setArtifact();
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public ThopterAssembly(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// At the beginning of your upkeep, if you control no Thopters other
		// than Thopter Assembly, return Thopter Assembly to its owner's hand
		// and put five 1/1 colorless Thopter artifact creature tokens with
		// flying onto the battlefield.
		this.addAbility(new ThopterAssemblyAbility1(state));
	}
}
