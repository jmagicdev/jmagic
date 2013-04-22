package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Encrust")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Encrust extends Card
{
	public static final class EncrustAbility1 extends StaticAbility
	{
		public EncrustAbility1(GameState state)
		{
			super(state, "Enchanted permanent doesn't untap during its controller's untap step and its activated abilities can't be activated.");

			EventPattern prohibitPattern = new UntapDuringControllersUntapStep(EnchantedBy.instance(This.instance()));
			SimpleEventPattern prohibitAbilities = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);

			prohibitAbilities.put(EventType.Parameter.OBJECT, new ActivatedAbilitiesOfPattern(EnchantedBy.instance(This.instance())));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(prohibitPattern, prohibitAbilities));
			this.addEffectPart(part);

		}
	}

	public Encrust(GameState state)
	{
		super(state);

		// Enchant artifact or creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Final(state, "artifact or creature", Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance())));

		// Enchanted permanent doesn't untap during its controller's untap step
		// and its activated abilities can't be activated.
		this.addAbility(new EncrustAbility1(state));
	}
}
