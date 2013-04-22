package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shifting Sky")
@Types({Type.ENCHANTMENT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PLANESHIFT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class ShiftingSky extends Card
{
	public static final class ShiftingSkyAbility0 extends org.rnd.jmagic.abilityTemplates.AsThisEntersTheBattlefieldChooseAColor
	{
		public ShiftingSkyAbility0(GameState state)
		{
			super(state, "Shifting Sky");

			this.getLinkManager().addLinkClass(ShiftingSkyAbility1.class);
		}
	}

	public static final class ShiftingSkyAbility1 extends StaticAbility
	{
		public ShiftingSkyAbility1(GameState state)
		{
			super(state, "All nonland permanents are the chosen color.");

			this.getLinkManager().addLinkClass(ShiftingSkyAbility0.class);

			SetGenerator objects = RelativeComplement.instance(Permanents.instance(), LandPermanents.instance());
			SetGenerator color = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.SET_COLOR);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, objects);
			part.parameters.put(ContinuousEffectType.Parameter.COLOR, color);
			this.addEffectPart(part);
		}
	}

	public ShiftingSky(GameState state)
	{
		super(state);

		// As Shifting Sky enters the battlefield, choose a color.
		this.addAbility(new ShiftingSkyAbility0(state));

		// All nonland permanents are the chosen color.
		this.addAbility(new ShiftingSkyAbility1(state));
	}
}
