package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Vedalken Shackles")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = FifthDawn.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class VedalkenShackles extends Card
{
	public static final class MayUntap extends StaticAbility
	{
		public MayUntap(GameState state)
		{
			super(state, "You may choose not to untap Vedalken Shackles during your untap step.");

			EventReplacementEffect replacement = new EventReplacementEffect(state.game, "You may choose not to untap Vedalken Shackles during your untap step.", new UntapDuringControllersUntapStep(This.instance()));
			replacement.makeOptional(You.instance());

			this.addEffectPart(replacementEffectPart(replacement));
		}
	}

	public static final class Shackle extends ActivatedAbility
	{
		public Shackle(GameState state)
		{
			super(state, "(2), (T): Gain control of target creature with power less than or equal to the number of Islands you control for as long as Vedalken Shackles remains tapped.");

			this.costsTap = true;
			this.setManaCost(new ManaPool("2"));

			SetGenerator islands = HasSubType.instance(SubType.ISLAND);
			SetGenerator youControl = ControlledBy.instance(You.instance());
			SetGenerator islandsYouControl = Intersect.instance(islands, youControl);
			SetGenerator numberOfIslandsYouControl = Count.instance(islandsYouControl);
			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(), HasPower.instance(Between.instance(Empty.instance(), numberOfIslandsYouControl))), "target creature with power less than or equal to the number of Islands you control");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, targetedBy(target));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());

			this.addEffect(createFloatingEffect(Intersect.instance(ABILITY_SOURCE_OF_THIS, Untapped.instance()), "Gain control of target creature with power less than or equal to the number of Islands you control for as long as Vedalken Shackles remains tapped.", part));
		}
	}

	public VedalkenShackles(GameState state)
	{
		super(state);

		this.addAbility(new MayUntap(state));

		this.addAbility(new Shackle(state));
	}
}
