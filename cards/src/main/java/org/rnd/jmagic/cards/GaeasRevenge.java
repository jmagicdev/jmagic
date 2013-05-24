package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Gaea's Revenge")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("5GG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class GaeasRevenge extends Card
{
	public static final class GaeasRevengeAbility2 extends StaticAbility
	{
		public GaeasRevengeAbility2(GameState state)
		{
			super(state, "Gaea's Revenge can't be the target of nongreen spells or abilities from nongreen sources.");

			SetGenerator nongreenSpells = RelativeComplement.instance(Spells.instance(), HasColor.instance(Color.GREEN));
			SetGenerator abilities = RelativeComplement.instance(InZone.instance(Stack.instance()), Spells.instance());
			SetGenerator nongreenAbilities = RelativeComplement.instance(abilities, HasColor.instance(Color.GREEN));
			SetGenerator nongreenStuff = Union.instance(nongreenSpells, nongreenAbilities);
			SimpleSetPattern nongreenPattern = new SimpleSetPattern(nongreenStuff);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CANT_BE_THE_TARGET_OF);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(nongreenPattern));
			this.addEffectPart(part);
		}
	}

	public GaeasRevenge(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(5);

		// Gaea's Revenge can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Gaea's Revenge can't be the target of nongreen spells or abilities
		// from nongreen sources.
		this.addAbility(new GaeasRevengeAbility2(state));
	}
}
