package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Riders of Gavony")
@Types({Type.CREATURE})
@SubTypes({SubType.KNIGHT, SubType.HUMAN})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class RidersofGavony extends Card
{
	public static final class RidersofGavonyAbility1 extends org.rnd.jmagic.abilities.AsThisEntersTheBattlefieldChooseACreatureType
	{
		public RidersofGavonyAbility1(GameState state)
		{
			super(state, "As Riders of Gavony enters the battlefield, choose a creature type.");

			this.getLinkManager().addLinkClass(RidersofGavonyAbility2.class);
		}
	}

	public static final class RidersofGavonyAbility2 extends StaticAbility
	{
		public static final class ProtectionName extends SetGenerator
		{
			public static ProtectionName instance(SetGenerator types)
			{
				return new ProtectionName(types);
			}

			private SetGenerator types;

			private ProtectionName(SetGenerator types)
			{
				this.types = types;
			}

			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				Set ret = new Set();
				for(SubType subType: this.types.evaluate(state, thisObject).getAll(SubType.class))
					ret.add(subType.toString() + " creatures");
				return ret;
			}

		}

		public RidersofGavonyAbility2(GameState state)
		{
			super(state, "Human creatures you control have protection from creatures of the chosen type.");

			SetGenerator chosenType = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));

			AbilityFactory abilityFactory = new org.rnd.jmagic.abilities.keywords.Protection.AbilityFactory(Intersect.instance(CreaturePermanents.instance(), chosenType), ProtectionName.instance(chosenType));

			this.addEffectPart(addAbilityToObject(Intersect.instance(CREATURES_YOU_CONTROL, HasSubType.instance(SubType.HUMAN)), abilityFactory));

			this.getLinkManager().addLinkClass(RidersofGavonyAbility1.class);
		}
	}

	public RidersofGavony(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// As Riders of Gavony enters the battlefield, choose a creature type.
		this.addAbility(new RidersofGavonyAbility1(state));

		// Human creatures you control have protection from creatures of the
		// chosen type.
		this.addAbility(new RidersofGavonyAbility2(state));
	}
}
