package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chief Engineer")
@Types({Type.CREATURE})
@SubTypes({SubType.VEDALKEN, SubType.ARTIFICER})
@ManaCost("1U")
@ColorIdentity({Color.BLUE})
public final class ChiefEngineer extends Card
{
	public static final class ChiefEngineerAbility0 extends StaticAbility
	{
		public ChiefEngineerAbility0(GameState state)
		{
			super(state, "Artifact spells you cast have convoke.");
			SetGenerator yourArtifactSpells = Intersect.instance(HasType.instance(Type.ARTIFACT), ControlledBy.instance(You.instance(), Stack.instance()));
			this.addEffectPart(addAbilityToObject(yourArtifactSpells, org.rnd.jmagic.abilities.keywords.Convoke.class));
		}
	}

	public ChiefEngineer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Artifact spells you cast have convoke. (Your creatures can help cast
		// those spells. Each creature you tap while casting an artifact spell
		// pays for (1) or one mana of that creature's color.)
		this.addAbility(new ChiefEngineerAbility0(state));
	}
}
