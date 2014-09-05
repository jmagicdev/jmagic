package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Painter's Servant")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.SCARECROW})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class PaintersServant extends Card
{
	public static final class ColorChoice extends org.rnd.jmagic.abilityTemplates.AsThisEntersTheBattlefieldChooseAColor
	{
		public ColorChoice(GameState state)
		{
			super(state, "Painter's Servant");

			this.getLinkManager().addLinkClass(Paint.class);
		}
	}

	public static final class Paint extends StaticAbility
	{
		public Paint(GameState state)
		{
			super(state, "All cards that aren't on the battlefield, spells, and permanents are the chosen color in addition to their other colors.");

			this.getLinkManager().addLinkClass(ColorChoice.class);

			SetGenerator cardsNotOnBattlefield = RelativeComplement.instance(Cards.instance(), InZone.instance(Battlefield.instance()));
			SetGenerator objects = Union.instance(cardsNotOnBattlefield, Union.instance(Spells.instance(), Permanents.instance()));
			SetGenerator color = ChosenFor.instance(LinkedTo.instance(Identity.instance(this)));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_COLOR);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, objects);
			part.parameters.put(ContinuousEffectType.Parameter.COLOR, color);
			this.addEffectPart(part);
		}
	}

	public PaintersServant(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new ColorChoice(state));

		this.addAbility(new Paint(state));
	}
}
